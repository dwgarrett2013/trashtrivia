# trashtrivia

## Class Structure

| Tables   |      Are      |  Cool |
|----------|:-------------:|------:|
| col 1 is |  left-aligned | $1600 |
| col 2 is |    centered   |   $12 |
| col 3 is | right-aligned |    $1 |

* User
    * id: String (Primary Key)
    * username: String (Primary Key)
    * password: String
    * role: String (Foreign Key to id in Role)
    * security_question_id: String (Foreign Key to id in Security_Question)
    * security_question_answer: String
    * num_correct_answer: int
    * num_question_completed: int
    * num_quizzes_taken: int
    
* Security_Question
    * id: String (Primary Key)
    * question_text: String
    
* Question
    * id: String (Primary Key)
    * question_text: String
    * question_answer_correct: String
    * question_answer_options: arrayList(String)
    * question_additional_information: String
    * num_times_asked: int  
    * num_times_answered_correctly: int
    * tags: arrayList(String foreign keys to Tag)
 
* Tag
    * id: String (Primary Key)
    * tag_text: String
    * num_tagged_questions_asked: int
    * num_tagged_questions_answered_correctly: int
    
* Session
    * id: String (Primary Key)
    * num_questions_to_ask: int
    * user_id: String (Foreign Key to User)
    * num_correct_answers: int
    * num_questions_completed: int

* Notification
    * id: String (Primary Key)
    * sender_id: String (Foreign Key to User) 
    * recipient_id: String (Foreign Key to User)
    * notification_text: String
    * notification_ts: Date
    

