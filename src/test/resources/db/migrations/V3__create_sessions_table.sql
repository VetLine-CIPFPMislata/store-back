
CREATE TABLE sessions (
    id_session BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_client BIGINT NOT NULL,
    token VARCHAR(500) NOT NULL UNIQUE,
    login_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_session_client FOREIGN KEY (id_client) REFERENCES clients(id_client) ON DELETE CASCADE
);


CREATE INDEX idx_session_token ON sessions(token);
CREATE INDEX idx_session_client ON sessions(id_client);

