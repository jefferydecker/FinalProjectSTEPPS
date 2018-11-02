# FinalProjectSTEPPS
Final Project Proposal

Data Structures in Java Fall 2018	Jeffery Decker	jmdecker1@dmacc.edu

Problem Description

Many of the forms and other instruments in the STEPPS program (Systems Training for Emotional Predictability and Problem Solving) would be more beneficial to the user, as well as to practitioners, if they were available in software form. Some of the tools have more broad application than just STEPPS, such as the Emotional Intensity Continuum, which is also used in Anger Management training, among other clinical settings. I propose to develop a suite of apps including the various forms and tools in STEPPS along with reference materials.

For this project, I propose to implement two tools: the BEST (Borderline Evaluation of Severity over Time) and the Filter Questionnaire (Adapted from Young, J.E. 2000. Cognitive Therapy for Personality Disorders: A Schema-Focused Approach. Professional Resource Press.)

The BEST consists of items of thoughts, feelings and negative behaviors that may cause distress along with the user’s ratings for each item and a list of positive behaviors with ratings indicating the frequency they were used during the rated period – typically seven or 30 days.

“A filter is an extremely stable and enduring pattern of thinking that develops during childhood and is elaborated throughout an individual’s life. We view the world through filters.” (Young, 1999) They are patterns of how we think about the world, ourselves and other people around us. Unhelpful filters are maintained through distorted thought patterns and self-defeating behavior. 

The Filter Questionnaire is a list of 60 statements in ten categories such as abandonment, subjugation and entitlement. For each question the user rates from zero to four how true a statement is for them when considering the previous year. The scores represent: 0 = Not true at all, 1 = Rarely true, 2 = Somewhat true, 3 = Mostly true, and 4 = Definitely true. There are six statements for each of the ten categories which are then summed up and sorted in descending order so that there will be a list ordering the categories from most prominent to least. It is beyond the scope of this proposal to describe what this information is used for beyond general self-awareness.

Proposed Solution

Both forms consist of statements to be rated, grouped into categories, three categories on the BEST and ten on the Filter Questionnaire. I intend to represent these in priority queues with a different priority for each category. The queues will be implemented so that items of the same priority will be dequeued in the order in which they were enqueued. Sums of ratings for each category are required, so as each priority level is exhausted, a sum will be generated and added to a list of sums whose size will be the number of categories on the given form. The overall score for the BEST form will be the sums of the first two categories minus the sum of the third. On the Filter Questionnaire there will then be ten sums, one for each category, which will then need to be sorted in descending order and presented with the corresponding names of each category.

On each form, the ratings will be stored in a list, probably an array, that is parallel to the order of the rated statements. After either form is completed, the results will be saved to the disk or possibly the cloud. If time allows, this will be done using SQL, probably mySQL or SQLite, otherwise data will be saved to a flat text file. Only the results and category totals will be saved along with the total score on the BEST and the sorted category list on the Filter Questionnaire. The date and a few other fields will be saved as well. 

Project Plan

Sprints 	->Deliverables

1	Create and test object classes for both forms.	->Object class code and working driver code

2	Learn Window builder and make UI prototype.	->Rudimentary screen form code

3	Flesh out UI and enter test forms	->Working (proof of concept prototype) application

4	Learn and evaluate MySQL, decide whether to use it rather than data files, and develop methods to save data 	->Working (proof of concept prototype) application with data files

