ALTER TABLE documents ADD COLUMN document_type VARCHAR(30);
ALTER TABLE documents ADD COLUMN processing_status VARCHAR(30) DEFAULT 'PENDING';