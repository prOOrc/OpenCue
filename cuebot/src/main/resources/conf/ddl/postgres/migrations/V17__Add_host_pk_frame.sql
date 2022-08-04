
-- Add pk_frame

ALTER TABLE host ADD COLUMN pk_frame VARCHAR(36);
ALTER TABLE host ADD CONSTRAINT c_host_pk_frame FOREIGN KEY (pk_frame) REFERENCES frame (pk_frame);