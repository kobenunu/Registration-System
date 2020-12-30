package bgu.spl.net.api;

import bgu.spl.net.srv.Database;

public class Protocol implements MessagingProtocol<Message>{

    private Database database;

    public Protocol(){
        database = Database.getInstance();
    }

    @Override
    public Message process(Message msg) {
        return msg.actOnProtocol(this);
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

    public void opcode1(String[] a){
        System.out.println("message's content is " + a[0] + " and " + a[1]);
    }
}
