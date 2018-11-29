# trashtrivia

## Class Structure

Below is a list of classes in the project and their fields

### User 
| Field   |      Type      |  Note |
|----------|:-------------:|------:|
|  id  |   String  |  (Primary Key)  |
|  username  |   String  |  (Primary Key)  |
|  password  |   String  |    |
|  role  |   String  |  (Foreign Key to id in Role)  |
|  security_question_id  |   String  |  (Foreign Key to id in Security_Question)  |
|  security_question_answer  |   String  |    |
|  num_correct_answer  |   int  |  (Foreign Key to id in Security_Question)  |
|  num_question_completed  |   int  |  (Foreign Key to id in Security_Question)  |
|  num_quizzes_taken  |   int  |  (Foreign Key to id in Security_Question)  |
    
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
    

