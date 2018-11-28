# trashtrivia

adding some details
retrieviing some details
bleh bleh bleh

## Class Structure

* User
    * id-String
    * username-String
    * password-String
    * role-String
    * security_question_id-int (Foreign Key to Security_Question id)
    * security_question_answer-String
    * num_correct_answer
    * num_question_completed
    * num_quizzes_taken
    
* Security_Question
    * id-String
    * question_text-String
    
* Question
    * id
    * question_text
    * question_answer_correct-String
    * question_answer_options-arrayList(String)
    * question_additional_information
    
    
* Session
    * id
    * num_questions_to_ask
    * user_id
    * num_correct_answers
    * num_questions_completed

* Session
    * id
    * num_questions_to_ask
    * user_id
    * num_correct_answers
    * num_questions_completed
    

