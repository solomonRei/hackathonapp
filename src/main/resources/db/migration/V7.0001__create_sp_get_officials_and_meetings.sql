CREATE OR REPLACE FUNCTION get_officials_and_meetings(p_user_id INT)
    RETURNS TABLE (
                      official_id INT,
                      official_name VARCHAR,
                      official_position VARCHAR,
                      official_bio TEXT,
                      meeting_id INT,
                      meeting_link VARCHAR,
                      meeting_date TIMESTAMP
                  ) AS $$
BEGIN
    RETURN QUERY
        SELECT
            o.id,
            o.name,
            o.position,
            o.bio,
            m.id,
            m.meeting_link,
            m.meeting_date
        FROM
            officials o
                INNER JOIN
            meetings m ON o.id = m.official_id
        WHERE
                o.user_id = p_user_id;
END;
$$ LANGUAGE plpgsql;
