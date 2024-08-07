package product_1.entities;

import java.util.List;

public class Bank {
    private String vrOrganization;
    private Boolean hasPichakService = ((Boolean) false);
    private String code;

    public List<PodServiceConfig> fetchPodServiceConfigOfBank(){
        return null;
    }
    public String getVrOrganization() {
        return vrOrganization;
    }

    public void setVrOrganization(String vrOrganization) {
        this.vrOrganization = vrOrganization;
    }

    public Boolean getHasPichakService() {
        return hasPichakService;
    }

    public void setHasPichakService(Boolean hasPichakService) {
        this.hasPichakService = hasPichakService;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
