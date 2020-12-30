package bgu.spl.net.api;

import java.util.Arrays;

public class AckMessage extends Message<Integer>{

    private Integer content;
    private String forClient;

    public AckMessage(int ackWhat) {
        super(12);
        content = ackWhat;
        forClient = null;
    }

    public AckMessage(int ackWhat, String messageForClient) {
        super(12);
        content = ackWhat;
        forClient = messageForClient;
    }

    public byte[] actOnEncoder(){
        byte[] op = IntToBytes(opcode);
        byte[] con = IntToBytes(content);
        byte[] message = forClient.getBytes();
        return merge(op, con, message);
    }



    @Override
    Message actOnProtocol(Protocol p) {
        return null;
    }

    @Override
    Integer getContent() {
        return content;
    }



}

