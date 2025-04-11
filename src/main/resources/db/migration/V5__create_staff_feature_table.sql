-- TABEL: tutor_application
CREATE TABLE IF NOT EXISTS tutor_application (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'))
    );

-- TABEL: course_request
CREATE TABLE IF NOT EXISTS course_request (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tutor_id UUID NOT NULL,
    course_title VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'))
    );

-- TABEL: transaction
CREATE TABLE IF NOT EXISTS transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID NOT NULL,
    course_id UUID NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('PENDING', 'PAID', 'FAILED'))
    );

-- TABEL: refund_request
CREATE TABLE IF NOT EXISTS refund_request (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_id UUID NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED'))
    );