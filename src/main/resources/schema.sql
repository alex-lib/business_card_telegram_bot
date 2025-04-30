INSERT INTO managers (last_name, first_name, user_name, id)
SELECT 'Дмитрий', 'Дмитриев', 'dmtkrv', 1303265124
WHERE NOT EXISTS (SELECT 1 FROM managers WHERE id = 1303265124);