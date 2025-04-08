SELECT
c.name AS customer,
o.order_date,
j.name AS jewelry,
oi.quantity,
oi.price AS unit_price,
(oi.quantity * oi.price) AS total_price
FROM Orders o
JOIN Customer c ON o.customer_id = c.id
JOIN OrderItem oi ON o.id = oi.order_id
JOIN Jewelry j ON oi.jewelry_id = j.id
WHERE o.order_date BETWEEN '2023-10-01' AND '2023-10-31'
ORDER BY o.order_date ASC
LIMIT 20;
