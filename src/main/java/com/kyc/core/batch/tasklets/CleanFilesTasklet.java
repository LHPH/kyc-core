package com.kyc.core.batch.tasklets;

import com.kyc.core.enums.MessageType;
import com.kyc.core.exception.KycBatchException;
import com.kyc.core.model.MessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class CleanFilesTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanFilesTasklet.class);

    private final String stepName;
    private final Resource resource;
    private final boolean silentIfError;
    private final MessageData messageData;

    public CleanFilesTasklet(String stepName, String path){
        this(stepName,path,true,new MessageData("ERROR","ERROR", MessageType.ERROR));
    }
    public CleanFilesTasklet(String stepName,String path, boolean silentIfError, MessageData messageData){

        this.resource = new FileSystemResource(path);
        this.stepName = stepName;
        this.silentIfError = silentIfError;
        this.messageData = messageData;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {

        try{
            if(resource.exists()){

                File file = resource.getFile();
                if(file.isFile()){

                    String name = file.getName();
                    LOGGER.info("Deleting {}",name);
                    boolean result = file.delete();
                    LOGGER.info("The file {} was deleted {}",name,result);
                    throwException(result,null);
                }
                else{
                    LOGGER.info("The path is directory, retrieve first level files");
                    File[] files = file.listFiles(File::isFile);
                    for(File fileElement : files){
                        String name = fileElement.getName();
                        LOGGER.info("Deleting {}",name);
                        boolean result = fileElement.delete();
                        LOGGER.info("The file {} was deleted {}",name,result);
                        throwException(result,null);
                    }
                }
            }
            else{
                LOGGER.warn("The resource to delete does not exists");
            }
        }
        catch(IOException ex){
            LOGGER.warn("An IO Error has happened due: ",ex);
            throwException(false,ex);
        }
        return RepeatStatus.FINISHED;
    }

    private void throwException(boolean result, Exception ex){

        if(!result && !this.silentIfError){

            throw KycBatchException.builderBatchException()
                    .exitStatus(ExitStatus.FAILED)
                    .exception(ex)
                    .errorData(this.messageData)
                    .batchStepName(stepName)
                    .build();
        }
    }
}
