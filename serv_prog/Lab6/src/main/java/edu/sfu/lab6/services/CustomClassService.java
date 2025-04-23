package edu.sfu.lab6.services;

import edu.sfu.lab6.model.CustomClass;
import edu.sfu.lab6.dao.CustomDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomClassService {

    private final CustomDAO customDAO;

    public CustomClassService(CustomDAO customDAO) {
        this.customDAO = customDAO;
    }

    public List<CustomClass> getCombinedData() {
        return customDAO.getCombinedData();
    }
}