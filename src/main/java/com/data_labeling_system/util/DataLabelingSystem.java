package com.data_labeling_system.util;

import com.data_labeling_system.model.Dataset;

import java.io.IOException;

public class DataLabelingSystem {

    private Dataset dataset;
    private IOManager ioManager;
    private UserManager userManager;
    private InstanceTagger instanceTagger;

    public DataLabelingSystem() {
        this.ioManager = new IOManager();
        this.userManager = new UserManager();
        this.instanceTagger = new InstanceTagger();

    }

    public void startSystem() throws IOException {
    	// Read json files and keep as string.
    	String configJson = this.ioManager.readInputFile("config.json");
        String datasetJson = this.ioManager.readInputFile("input-2.json");
        // Create dataset and user objects using the json strings.
        dataset = new Dataset(datasetJson);
        userManager.createUsers(configJson);
        // Assign users using the UserManager class.
        this.dataset.setUsers(userManager.getUsers());
        // Assign updated objects to the instanceTagger object.
        this.instanceTagger.setDataset(this.dataset);
        this.instanceTagger.setUsers(this.userManager.getUsers());
        // Assign label to instances.
        this.instanceTagger.assignLabels();
        // Take final dataset and write as json file
        this.dataset = this.instanceTagger.getDataset();
        this.ioManager.printFinalDataset(this.dataset, "output.json");
    }
}
