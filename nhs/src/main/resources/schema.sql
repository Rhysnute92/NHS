DROP TABLE IF EXISTS user_widget;
CREATE TABLE user_widget (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    widget_name VARCHAR(255) NOT NULL,
    position INT NOT NULL
);