SELECT
	c.name AS customer_name,
	c.phone AS customer_phone,
	c.email AS customer_email,
	o.order_date,
	j.name AS jewelry_name,
	jt.name AS jewelry_type,
	STRING_AGG(m.name, ', ') AS materials,
	oi.quantity,
	oi.price AS unit_price,
	(oi.quantity * oi.price) AS total_price
FROM Customer c
JOIN Orders o ON c.id = o.customer_id
JOIN OrderItem oi ON o.id = oi.order_id
JOIN Jewelry j ON oi.jewelry_id = j.id
JOIN JewelryType jt ON j.type_id = jt.id
LEFT JOIN JewelryMaterial jm ON j.id = jm.jewelry_id
LEFT JOIN Material m ON jm.material_id = m.id
GROUP BY
	c.name,
	c.phone,
	c.email,
	o.order_date,
	j.name,
	jt.name,
	oi.quantity,
	oi.price,
	o.id,
	j.id
ORDER BY o.order_date ASC
LIMIT 10;

