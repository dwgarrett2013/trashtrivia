# trashtrivia

## Class/Database Structure

Below is a list of classes/database objects in the project and their fields.

### User 
| Field   |      Type      |  Note |
|:----------:|:-------------:|:------:|
|  id  |   String  |  Primary Key  |
|  username  |   String  |  Primary Key  |
|  password  |   String  |    |
|  role  |   String  |  Foreign Key to id in Role  |
|  security_question_id  |   String  |  Foreign Key to id in Security_Question  |
|  security_question_answer  |   String  |    |
|  num_correct_answer  |   int  |  Foreign Key to id in Security_Question  |
|  num_question_completed  |   int  |  Foreign Key to id in Security_Question  |
|  num_quizzes_taken  |   int  |  Foreign Key to id in Security_Question  |

### Role 
| Field   |      Type      |  Note |
|:----------:|:-------------:|:------:|
|  id  |   String  |  Primary Key  |
|  role_name  |   String  |  Primary Key  |
    
### Security_Question
| Field   |      Type      |  Note |
|:----------:|:-------------:|:------:|
|  id  |   String  |  Primary Key  |
|  security_question_text  |   String  |    |

### Question
| Field   |      Type      |  Note |
|:----------:|:-------------:|:------:|
|  id  |   String  |  Primary Key  |
|  question_text  |   String  |    |
|  question_answer_correct  |   String  |    |
|  question_answer_options  |   arrayList(String)  |    |
|  question_additional_information  |   String  |    |
|  num_times_asked  |   int  |    |
|  num_times_answered_correctly  |   int  |    |
|  tags  |   arrayList(String)  |    |
|  question_text  |   String  |  String elements are Foreign keys to Tag  |

### Tag
| Field   |      Type      |  Note |
|:----------:|:-------------:|:------:|
|  id  |   String  |  Primary Key  |
|  tag_text  |   String  |    |
|  num_tagged_questions_asked  |   int  |    |
|  num_tagged_questions_answered_correctly  |   int  |    |
    
### Session
| Field   |      Type      |  Note |
|:----------:|:-------------:|:------:|
|  id  |   String  |  Primary Key  |
|  num_questions_to_ask  |   int  |    |
|  user_id  |   String  |  Foreign Key to User  |
|  num_correct_answers  |   int  |    |
|  num_questions_completed  |   int  |    |

### Notification
| Field   |      Type      |  Note |
|:----------:|:-------------:|:------:|
|  id  |   String  |  Primary Key  |
|  sender_id  |   String  |  Foreign Key to User  |
|  recipient_id  |   String  |  Foreign Key to User  |
|  notification_text  |   String  |    |
|  notification_ts  |   Timestamp  |    |
|  num_questions_completed  |   int  |    |

