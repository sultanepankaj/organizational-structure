package org.structure.application;

import org.structure.report.ReadFile;

public class OrganizationStructure {

    public static void main(String[] args) {
        OrganizationStructure organizationStructure = new OrganizationStructure();
        organizationStructure.readFile();
    }

    private void readFile() {
        ReadFile readFile = new ReadFile();
        readFile.readAndMapFile();
    }
}
