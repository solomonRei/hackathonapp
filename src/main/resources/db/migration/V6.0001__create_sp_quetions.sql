CREATE OR REPLACE PROCEDURE get_questions(
    p_meeting_id INT DEFAULT NULL,
    p_question_id INT DEFAULT NULL,
    p_user_id INT DEFAULT NULL,
    p_title_filter VARCHAR DEFAULT NULL
)
    LANGUAGE plpgsql
AS $$
BEGIN
    IF p_meeting_id IS NOT NULL THEN
        -- Fetch questions for a specific meeting
        RETURN QUERY
            SELECT * FROM questions WHERE meeting_id = p_meeting_id;
    ELSIF p_question_id IS NOT NULL THEN
        -- Fetch a specific question by question_id
        RETURN QUERY
            SELECT * FROM questions WHERE question_id = p_question_id;
    ELSIF p_user_id IS NOT NULL THEN
        -- Fetch questions for a specific user
        RETURN QUERY
            SELECT * FROM questions WHERE user_id = p_user_id;
    ELSIF p_title_filter IS NOT NULL THEN
        -- Fetch questions that match a title filter
        RETURN QUERY
            SELECT * FROM questions WHERE question_title ILIKE '%' || p_title_filter || '%';
    ELSE
        -- If no parameters are provided, return all questions
        RETURN QUERY
            SELECT * FROM questions;
    END IF;
END;
$$;
