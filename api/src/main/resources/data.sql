INSERT IGNORE INTO address (id, street, house_number, postal_code, city, country, created)
VALUES (1, '', '', '', '', '', CURRENT_DATE()),
       (2, '', '', '', '', '', CURRENT_DATE()),
       (3, '', '', '', '', '', CURRENT_DATE()),
       (4, '', '', '', '', '', CURRENT_DATE());

INSERT IGNORE INTO user (id, email, password, display_name, first_name, last_name, date_of_birth, gender, address_id, role, shipping_address_id, created)
VALUES (1, 'admin@firesale.nl', '$2a$10$zO.P/jNe8LedLkSAD67AIOXOktIBbsncYE7VcP/cUxvgkiZ4dBgRi', 'Admin', '', '', CURRENT_DATE(), 'OTHER', 1, 'ADMIN', 2, CURRENT_DATE()), /* password = admin */
       (2, 'user@firesale.nl', '$2a$10$aqnyqYtZbRbVa58vzFlHv.FauhaSYyfnwHcGUXCJJCaFTboubGxJq', 'User', '', '', CURRENT_DATE(), 'OTHER', 3, 'USER', 4, CURRENT_DATE()); /* password = user */