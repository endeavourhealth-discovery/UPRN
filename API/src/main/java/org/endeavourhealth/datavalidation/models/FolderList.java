package org.endeavourhealth.datavalidation.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class FolderList {

    private List<Folder> folders = new ArrayList<>();

    public FolderList() {
    }

    public void add(Folder jsonFolder) {
        folders.add(jsonFolder);

        //find the next non-null index
        /*for (int i=0; i<folders.length; i++)
        {
            if (folders[i] == null)
            {
                folders[i] = jsonFolder;
                return;
            }
        }

        throw new RuntimeException("Trying to add too many organisations to JsonOrganisationList");*/
    }
    /*public void add(DbFolder folder, int count)
    {
        Folder jsonFolder = new Folder(folder, count);
        add(jsonFolder);
    }*/


    /**
     * gets/sets
     */
    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }
}
