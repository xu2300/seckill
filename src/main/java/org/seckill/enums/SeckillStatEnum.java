package org.seckill.enums;

/**
 * Created by junweixu on 16/12/27.
 */
public enum SeckillStatEnum {
    SUCCESS(1,"SUCCESS KILL"),
    END(0,"KILL END"),
    REPEAT_KILL(-1,"REPEAT KILL"),
    INNER_ERROR(-2,"SYSTEM ERROR"),
    DATA_REWRITE(-3,"DATA REWRITE");
    private int state;

    private String stateInfo;

    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStatEnum stateOf(int index){
        for(SeckillStatEnum state:values()){
            if(state.getState() == index){
                return state;
            }
        }
        return null;
    }
}
