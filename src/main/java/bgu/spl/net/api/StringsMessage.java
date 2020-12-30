package bgu.spl.net.api;

import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.User;

public class StringsMessage extends Message<String[]>{

    final private String[] content;
    private Database database;

    public StringsMessage(int op, String[] content) {
        super(op);
        this.content = content;
        database = Database.getInstance();
    }

    @Override
    public Message actOnProtocol(Protocol p) {
        if (opcode==1){
            return opcode1(p);
        }
        if (opcode==2){
            return opcode2(p);
        }
        if (opcode==3){
            return opcode3(p);
        }
    }

    @Override
    byte[] actOnEncoder() {
        return null;
    }

    private Message opcode1(Protocol p){
        if (p.getHandler().getUser()==null){
            User newAdmin = database.registerAdmin(content[0], content[1]);
            if (newAdmin!=null) {
                p.getHandler().setUser(newAdmin);
                return createACK(opcode, "registered successfully");
            }
        }
        return createError(opcode);
    }

    private Message opcode2(Protocol p){
        if (p.getHandler().getUser()==null){
            User newStudent = database.registerStudent(content[0], content[1]);
            if (newStudent!=null) {
                p.getHandler().setUser(newStudent);
                return createACK(opcode, "registered successfully");
            }
        }
        return createError(opcode);
    }

    private Message opcode3(Protocol p){
        User logAttempt = p.getHandler().getUser();
        if (logAttempt!=null){
            try{
                database.logIn(content[0], content[1]);
            }
            catch (IllegalAccessException i){
                i.printStackTrace();
                return createError(opcode);
            }
            catch (IllegalArgumentException i){
                i.printStackTrace();
                return createError(opcode);
            }
            return createACK(opcode, "Logged in successfully");
        }
        return createError(opcode);
    }

    private Message createError(int errorFor){
        return new ErrorMessage(errorFor);
    }

    private Message createACK(int ackWhat, String messageForClient){
        return new AckMessage(ackWhat, messageForClient);
    }

    public String[] getContent(){
        return content;
    }
}
