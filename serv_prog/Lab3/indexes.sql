
-- Для фильтрации по цене и весу в таблице Jewelry
CREATE INDEX idx_jewelry_price_weight ON Jewelry(price, weight);

-- Для фильтрации по country_id в Jewelry
CREATE INDEX idx_jewelry_country_id ON Jewelry(country_id);

-- Для фильтрации по типу материала в Material
CREATE INDEX idx_material_type ON Material(type);

-- bitmap
CREATE INDEX idx_jewelry_price_bitmap ON Jewelry USING btree (price);

-- Для соединения JewelryMaterial с Jewelry
CREATE INDEX idx_jewelrymaterial_jewelry_id ON JewelryMaterial(jewelry_id);

-- Для соединения JewelryMaterial с Material
CREATE INDEX idx_jewelrymaterial_material_id ON JewelryMaterial(material_id);



