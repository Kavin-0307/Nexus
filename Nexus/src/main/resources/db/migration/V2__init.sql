
CREATE TABLE documents (
    document_id BIGSERIAL PRIMARY KEY,

    document_name VARCHAR(255) NOT NULL,

    content TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    user_id BIGINT NOT NULL,

    CONSTRAINT fk_documents_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_documents_user_id
ON documents(user_id);
