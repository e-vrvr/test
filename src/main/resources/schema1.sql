DROP TABLE IF EXISTS posts;

CREATE TABLE posts (
  postId INTEGER PRIMARY KEY AUTO_INCREMENT,
  content VARCHAR(256),
  subject VARCHAR(50),
  tags VARCHAR(1024),
  modificationDate DATE,
  authorName VARCHAR(64),
  authorUserName VARCHAR(64)
);
