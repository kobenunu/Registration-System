package bgu.spl.net.api;

public class IntegerMessage extends Message<Integer>{

    final private Integer content;

    public IntegerMessage(int op, Integer i) {
        super(op);
        content = i;
    }



    @Override
    Message actOnProtocol(Protocol p) {
        return null;
    }

    @Override
    byte[] actOnEncoder() {
        return null;
    }

    @Override
    public Integer getContent() {
        return content;
    }
}
