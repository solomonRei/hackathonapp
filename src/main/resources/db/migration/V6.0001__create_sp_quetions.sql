CREATE OR REPLACE FUNCTION get_filtered_questions(
    p_meeting_id INT DEFAULT NULL,
    p_question_id INT DEFAULT NULL,
    p_user_id INT DEFAULT NULL,
    p_title_filter VARCHAR DEFAULT NULL
)
    RETURNS SETOF questions AS $$
DECLARE
    where_clause TEXT := '';
BEGIN
    IF p_meeting_id IS NOT NULL THEN
        where_clause := where_clause || ' AND meeting_id = ' || p_meeting_id::TEXT;
    END IF;

    IF p_question_id IS NOT NULL THEN
        where_clause := where_clause || ' AND question_id = ' || p_question_id::TEXT;
    END IF;

    IF p_user_id IS NOT NULL THEN
        where_clause := where_clause || ' AND user_id = ' || p_user_id::TEXT;
    END IF;

    IF p_title_filter IS NOT NULL THEN
        where_clause := where_clause || ' AND question_title ILIKE ''%' || p_title_filter || '%''';
    END IF;

    IF LENGTH(where_clause) > 0 THEN
        where_clause := SUBSTRING(where_clause FROM 5);
    END IF;

    RETURN QUERY EXECUTE 'SELECT * FROM questions WHERE ' || where_clause
        USING p_meeting_id, p_question_id, p_user_id, p_title_filter;

    IF where_clause = '' THEN
        RETURN QUERY SELECT * FROM questions;
    END IF;
END;
$$ LANGUAGE plpgsql;
