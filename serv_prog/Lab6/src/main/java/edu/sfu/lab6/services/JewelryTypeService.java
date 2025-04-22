package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.JewelryTypeDAO;
import edu.sfu.lab6.model.JewelryType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JewelryTypeService {

    private final JewelryTypeDAO jewelryTypeDAO;

    public JewelryTypeService(JewelryTypeDAO jewelryTypeDAO) {
        this.jewelryTypeDAO = jewelryTypeDAO;
    }

    public List<JewelryType> getAllJewelryTypes() {
        return jewelryTypeDAO.getAll();
    }

    public JewelryType createJewelryType(JewelryType jewelryType) {
        Integer id = jewelryTypeDAO.save(jewelryType);
        return jewelryTypeDAO.getById(id);
    }
}