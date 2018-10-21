CREATE TABLE apachelog (
  l_uniqueid CHAR(24) NOT NULL,
  l_time CHAR(14) NOT NULL,
  l_needsresolution TINYINT(1) NOT NULL,
  l_needsexport TINYINT(1) NOT NULL,
  l_vhost VARCHAR(100) NOT NULL,
  l_logentry TEXT NOT NULL,
  PRIMARY KEY (l_uniqueid),
  KEY idx_1 (l_needsresolution, l_needsexport, l_vhost),
  KEY idx_2 (l_time)
) ENGINE = InnoDB;

CREATE TABLE vhosts (
  v_vhost VARCHAR(100) NOT NULL,
  v_logdirectory VARCHAR(200) NOT NULL,
  PRIMARY KEY (v_vhost),
  UNIQUE KEY idx_1 (v_logdirectory)
) ENGINE = InnoDB;
