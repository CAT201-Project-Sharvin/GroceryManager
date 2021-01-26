## Grocery Manager

<p align="center"><i>One stop solution to track your grocery list and suggest you with daily recipes</i></p>

<img src="

## Introduction

<p> Grocery
Manager is an app to solve all your problems as it manag es all of your groceries and
keep s up with daily cooking. This app is more like your virtual refrigerator where you do not
have stumble upon things when you are buying groceries in a grocery stor e by cracking your
head if you have already bought those groceries or not. This app has yet to offer you with two
of its main features which is tracking your grocery items which is in your refrigerator and
suggesting you with daily recipes based on the ingredie nts you have. First tracking your
grocery list feature will have four sub features which is adding, view ing updating and
deleting your groceries. Then the second main feature would be suggesting recipe s based on
the grocer ies you have left in your fridge The recipe feature will generate recipe suggestions
for breakfast, lunch and dinner based on the groceries we stored in your refrigerator. Apart
from that, to make your life easy it will always keep y ou on track about your groceries as the
app will push you notification if there is any grocery that has just expired. Then, to not keep
you waiting for thinking what you should cook today, the app will push notification an hour
earlier before y our breakfast, lunch and dinner and upon clicking the notification you will be
redirected to the recipe page. There is one feature that was also implemented in the app which
is to guide about some interesting kitchen tips. This feature will offer you wi th step by step
guide for each tutorial and also there will be a youtube video also played to guide you
visually. One last feature which was included in the app is the feature to show you where can
you get the groceries nearest. To give life to this app th ere are several external services used
such as Firebase, SpooncalurAPI and WebScrapping using Jsoup. The Firebase service is
basically used for authentication, real time database and storage. The authentication will be
made securely via FirebaseAuthentication API where all the users information are protected
and encrypted. Secondly, the Firebase Real time database used to store alphanumeric details
such as useruser’s name and their grocery information. Meanwhile, Firebase Storage is used to
store big files such as the photo of the useruser’s grocery. Then SpoonacularAPI is used to
generate the recipe suggestions. Every time, when the user land in the recipe page, the data of
their grocery list will be extracted from the real time database and passed in the API URL as
a par ameter, later API called be made using that URL. After the after gives a success
response and return a JSON object. The recipes will be extracted from JSON object. Lastly,
web scrapping is done to scrap the details for the kitchen tips section. This is bec ause the
websites that offer kitchen tips does not have their own API thus their websites are scrapped
to extract the details. </p>

## Motivation

<p> This app is invented to help many households. Many people tend to over buy their groceries as they don’t really remember of what is inside their refrigerator and after buying their groceries they will come to a realisation that it has always been in their refrigerator. This leads to wastage of foods. But our app can solve this problem by simply showing the list of items that are already in the refrigerator, so when the user arrives at the grocery store they do not have to crack their head thinking of what is in their refrigerator and what is not. They can simply open the app and view the list, later buy the groceries that is not in their fridge. This way we can overcome the problem of overbuying groceries. Next, many people can often track the dates of when they bought their groceries since we sometimes tend to overlook the expiry date and we may happen to eat the spoilt food. This can lead to many health issues. However, we have solved this problem by simply notifying the user if their grocery is about to expire in three days and this makes the user to simply keep track of the expiry dates on their food. Next, many young students who live far away from their family often do not get to eat good food and this way it allows them to buy groceries from nearby grocery stores. They might not know on how to utilise the groceries to make an edible food so our app solves this problem by suggesting the user awesome recipes for their breakfast, lunch and dinner based on the ingredients they have. This way students can get good food at the same time they can save their money by making homemade foods. The app will also have a feature to give kitchen tips which is helpful when it comes to cooking and with this way everyone will be able to cook. Apart from managing the groceries this app intends to promote cooking among youngsters.</p>
