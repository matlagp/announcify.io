--- !Ups
CREATE TABLE announcements_tags (
  tag_id SERIAL,
  name VARCHAR UNIQUE NOT NULL,
  description TEXT NOT NULL
);
CREATE INDEX idx_announcements_tags_name ON announcements_tags(name);

INSERT INTO announcements_tags (name, description) VALUES ('test', 'Test tag');

--- !Downs
DROP TABLE IF EXISTS announcements_tags;