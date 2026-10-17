package com.kyc.core.support;

public class SonyFlakeId {

    // Bit lengths of components
    private static final int BIT_LEN_TIME = 39;
    private static final int BIT_LEN_SEQUENCE = 8;
    private static final int BIT_LEN_MACHINE_ID = 16;

    // Bit masks
    private static final long MAX_SEQUENCE = (1L << BIT_LEN_SEQUENCE) - 1; // 255
    private static final long MAX_MACHINE_ID = (1L << BIT_LEN_MACHINE_ID) - 1; // 65535
    private static final long MAX_ELAPSED_TIME = (1L << BIT_LEN_TIME) - 1;

    // Shift offsets
    private static final int SHIFT_TIME = BIT_LEN_SEQUENCE + BIT_LEN_MACHINE_ID;
    private static final int SHIFT_SEQUENCE = BIT_LEN_MACHINE_ID;

    // Default Epoch: 2014-09-01T00:00:00Z (as per original Sonyflake implementation)
    private static final long DEFAULT_START_TIME = 1409529600000L;

    private final long startTime;
    private final long machineId;

    // Mutable state protected by synchronized lock
    private long sequence = 0L;
    private long lastTimestamp = -1;


    public SonyFlakeId(long machineId){
        this(machineId,DEFAULT_START_TIME);
    }

    /**
     * Constructs a Sonyflake generator with a custom epoch and explicit machine ID.
     *
     * @param startTimeMillis Custom start epoch timestamp in milliseconds. Must be in the past.
     * @param machineId Distinct ID assigned to this node (0 to 65535).
     */
    public SonyFlakeId(long machineId, long startTimeMillis){

        if(machineId < 0 || machineId > MAX_MACHINE_ID){
            throw new IllegalArgumentException("Machine id must be between 0 and " + MAX_MACHINE_ID);
        }
        if(System.currentTimeMillis()<startTimeMillis){
            throw new IllegalArgumentException("Start time is ahead of the current time");
        }

        this.machineId = machineId;
        this.startTime = startTimeMillis;
    }

    /**
     * Generates the next globally unique 64-bit ID.
     *
     * @return a unique primitive long ID.
     * @throws IllegalStateException if the system clock rolls back beyond recovery or max lifetime is exceeded.
     */
    public synchronized long nextId(){

        long currentTimestamp = getCurrentTimeBucket();
        if(currentTimestamp < this.lastTimestamp){
            throw new IllegalStateException("Clock moved backwards");
        }

        if(currentTimestamp == this.lastTimestamp){
            this.sequence = (this.sequence + 1) & MAX_SEQUENCE;
            if(this.sequence == 0){
                //Sequence overflow in this 10ms bucket, wait for next bucket
                currentTimestamp = waitNextTimeBucket(this.lastTimestamp);
            }
        }
        else{
            this.sequence = 0L;
        }

        this.lastTimestamp = currentTimestamp;

        if (this.lastTimestamp > MAX_ELAPSED_TIME) {
            throw new IllegalStateException("Over the time limit. Generator has reached its maximum lifespan.");
        }

        //Bit shifting logic to assemble the 63-bit long
        return (currentTimestamp << (SHIFT_TIME))
                | (sequence << SHIFT_SEQUENCE)
                | machineId;
    }

    //Returns time elapsed since custom epoch in units  of 10 milliseconds
    private long getCurrentTimeBucket(){
        return (System.currentTimeMillis() - this.startTime) / 10;
    }

    private long waitNextTimeBucket(long lastTimestamp){

        long timestamp =  getCurrentTimeBucket();
        while(timestamp <= lastTimestamp){
            timestamp = getCurrentTimeBucket();
        }
        return timestamp;
    }
}
