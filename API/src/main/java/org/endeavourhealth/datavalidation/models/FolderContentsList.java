package org.endeavourhealth.datavalidation.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class FolderContentsList {
    private List<FolderContent> contents = null;

    public FolderContentsList() {
    }



    public void addContent(FolderContent content) {
        if (contents == null) {
            contents = new ArrayList<FolderContent>();
        }
        contents.add(content);
    }


    /**
     * gets/sets
     */
    public List<FolderContent> getContents() {
        return contents;
    }

    public void setContents(List<FolderContent> contents) {
        this.contents = contents;
    }


}
