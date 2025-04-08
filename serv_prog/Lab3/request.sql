SELECT 
    j.id AS jewelry_id,
    j.name AS jewelry_name,
    j.price,
    j.weight,
    m.name AS material_name,
    m.type AS material_type,
    m.carat
FROM 
    Jewelry j
JOIN 
    JewelryMaterial jm ON j.id = jm.jewelry_id
JOIN 
    Material m ON jm.material_id = m.id
WHERE 
    j.price BETWEEN 500 AND 5000  
    AND m.type = 'gemstone'      
    AND j.weight > 10            
    AND j.country_id IN (        
        SELECT id FROM Country 
        WHERE name IN ('Россия', 'Италия', 'Франция')
    )
ORDER BY 
    j.price DESC
LIMIT 20;

