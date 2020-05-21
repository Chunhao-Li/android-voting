# Voting Data Structure #
Genrally, this data structure is aimed to hold all info for a voting event from the creator to all voters on app.
## Voting ##
This abstract class is used for template and further extension, you don't need to see this.
<br><br>
## 1 Voter Receiving Part ##
The following part is used for voter's app to receive. It should receive a VotingVoter object from server.


## VotingVoter ##
Which extends Voting, stores the following data:
* 1 Questions: contains all types questions by **ArrayList**, using **getQuestions** to get.
* 2 Creator UID: who creates this voting event by **String**, using **getCreatorUid** to get.
* 3 Voting UID:  voting unique UID by **String**, using **getVotingUid** to get.
* 4 Deadline: when will this voting event ends by **Date**,using **getDeadline** to get.

### Question ###
This abstract class is used for template and further extension, you don't need to see this.

### MultipleChoiceQuestion ###
 which is used in VotingVoter, stores:
 * 1 choices: several choices for current question by **ArrayList**, using **getChoices** to get
 * 2 questionString: title of question by **String**, using **getQuestionString** to get.
 * 3 questionType: type of question by **QuestionType**, using **getQuestionType**  to get.

 ### TextQuestion ###
 which is used in VotingVoter, stores:
 * 1 questionString: title of question by **String**, using **getQuestionString** to get.
 * 2 questionType: type of question by **QuestionType**, using **getQuestionType**  to get.
<br><br>

## 2 Creator Send & Receive Part
The following part is used for creator's app to receive and send. It should receive a VotingResults object from server and send both VotingResults and VotingVoter to server.
## VotingResults ##
Which extends Voting, stores the following data:
* 1 questionStatistics: questions with background data by **ArrayList**, using **getQuestionStatistics** to get.
* 2 Creator UID: who creates this voting event by **String**, using **getCreatorUid** to get.
* 3 Voting UID:  voting unique UID by **String**, using **getVotingUid** to get.
* 4 Deadline: when will this voting event ends by **Date**,using **getDeadline** to get.
<br><br>
Method:
* 5 addAnswers: If you need to add answer (with Type UserAnswers) to VotingResults, you should use **addAnswers**.

## MultipleChoiceQuestionStatistics ##
* 1 choices: several choices for current question by **ArrayList**, using **getChoices** to get.
* 2 questionString: title of question by **String**, using **getQuestionString** to get.
* 3 questionType: type of question by **QuestionType**, using **getQuestionType**  to get.
* 4 choiceVoterCount: each choice has choiceVoterCount[i] voters vote, by **Array**, using **getChoiceVoterCount** to get.
* 5 totalVoterCount: Number of voters answer this question, by **Interger**, using **getTotalVoterCount** to get.
<br><br>
Method:
* 6: update: automatic call in **addAnswers** for question status update.

## TextQuestionStatistics ##
* 1 answers: store all voter-answer by **HashMap<String,String>**, using **getAnwsers**.
* 2 well you can also get: TotalVoterCount, QuestionType, QuestionString as before.
<br><br>
Method:
* 3: update: automatic call in **addAnswers** for question status update.

<br><br>
## 3 Voter Sending Part ##
Obviously, the answer of voter sends to server.

## UserAnswers ##
which is the final object that voters send.
* 1 Voting UID:  voting unique UID by **String**, using **getVotingUid** to get.
* 2 respondent UID: voter unique UID by **String**, using **getRespondentUid** to get.
* 3 answers: **ArrayList** of answer Type, using **getAnswers** to get.

## MultipleChoiceAnswer ##
which is one of extension of abstract answer class.
* 1 answerChoice: **String**, the description of chosen choice, using **getAnswerString** to get.
* 2 same that has questionString, questionType,respondentUid inside.

## TextAnswer ##
which is one of extension of abstract answer class.
* 1 answerText: **String**, the answer text of voters, using **getAnswerString** to get.
* 2 same that has questionString, questionType,respondentUid inside.
