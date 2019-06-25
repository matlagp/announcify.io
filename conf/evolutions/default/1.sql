--- !Ups
CREATE TABLE announcements_tags (
  tag_id SERIAL,
  name VARCHAR UNIQUE NOT NULL,
  description TEXT NOT NULL
);
CREATE TABLE announcements (
  id SERIAL,
  title VARCHAR,
  content TEXT,
  authorId INTEGER,
  year INTEGER
);
CREATE INDEX idx_announcements ON announcements(authorId);

CREATE INDEX idx_announcements_tags_name ON announcements_tags(name);

INSERT INTO announcements (title,content,authorId,year) VALUES ('testowyAnnouncement','TO JEST TEKST',1,2015)

INSERT INTO announcements_tags (name, description) VALUES ('test', 'Test tag');

--- !Downs
DROP TABLE IF EXISTS announcements_tags;
DROP TABLE IF EXISTS announcements;