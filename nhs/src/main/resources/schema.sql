DROP TABLE IF EXISTS user_widgets;
CREATE TABLE user_widgets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    widget_name VARCHAR(255) NOT NULL,
    position INT NOT NULL
);