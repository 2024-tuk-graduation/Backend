INSERT INTO room (entrance_code, host_nickname, language, room_name, room_count, created_at, updated_at, is_deleted)
VALUES ('1a2s3d', 'host', 'python', 'Language Study', 4, NOW(), NOW(), false);

INSERT INTO participant (nickname, room_id, created_at, updated_at, is_deleted)
VALUES ('host', 1, NOW(), NOW(), false);
