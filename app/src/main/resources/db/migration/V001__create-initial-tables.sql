-- Tabela de usuários
CREATE TABLE tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP
);

-- Tabela de perfis
CREATE TABLE tb_profiles (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(255) CHECK (role IN ('ADMIN', 'MODERATOR', 'MEMBER'))
);

-- Tabela intermediária entre usuários e perfis
CREATE TABLE tb_users_profiles (
    user_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    PRIMARY KEY (profile_id, user_id),
    FOREIGN KEY (user_id) REFERENCES tb_users(id),
    FOREIGN KEY (profile_id) REFERENCES tb_profiles(id)
);

-- Tabela de categorias
CREATE TABLE tb_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    crated_by_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    FOREIGN KEY (crated_by_id) REFERENCES tb_users(id)
);

-- Tabela de cursos
CREATE TABLE tb_courses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    crated_by_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    FOREIGN KEY (crated_by_id) REFERENCES tb_users(id)
);

-- Tabela intermediária entre cursos e categorias
CREATE TABLE tb_courses_categories (
    course_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (category_id, course_id),
    FOREIGN KEY (course_id) REFERENCES tb_courses(id),
    FOREIGN KEY (category_id) REFERENCES tb_categories(id)
);

-- Tabela de tópicos
CREATE TABLE tb_topics (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(255) CHECK (status IN ('NO_REPLIES', 'WITH_REPLIES', 'SOLVED')),
    course_id BIGINT,
    author_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES tb_courses(id),
    FOREIGN KEY (author_id) REFERENCES tb_users(id)
);

-- Tabela de respostas
CREATE TABLE tb_replies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    solution TEXT NOT NULL,
    topic_id BIGINT,
    author_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    FOREIGN KEY (topic_id) REFERENCES tb_topics(id),
    FOREIGN KEY (author_id) REFERENCES tb_users(id)
);
