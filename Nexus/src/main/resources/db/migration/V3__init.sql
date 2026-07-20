CREATE TABLE document_collaborator (
    collaborator_id BIGSERIAL PRIMARY KEY,

    document_id BIGINT NOT NULL,

    user_id BIGINT NOT NULL,

    role VARCHAR(30) NOT NULL,

    added_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_documents
        FOREIGN KEY (document_id)
        REFERENCES documents(document_id),

    CONSTRAINT fk_users
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT uk_document_user
        UNIQUE(document_id, user_id)
);