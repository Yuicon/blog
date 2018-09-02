CREATE TABLE tb_article (
  id             BIGINT(20)   NOT NULL AUTO_INCREMENT,
  gitUserName    VARCHAR(255) NOT NULL,
  repositoryName VARCHAR(255) NOT NULL,
  issueId        BIGINT(20)   NOT NULL,
  title          VARCHAR(255),
  createdAt      VARCHAR(255),
  updatedAt      VARCHAR(255),
  closedAt       VARCHAR(255),
  body           TEXT,
  UNIQUE (gitUserName, repositoryName, issueId),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;
