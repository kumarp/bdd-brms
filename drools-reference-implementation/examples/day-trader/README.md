BDD/BRMS Demo

LINKS
rhcdemo GitHub: https://github.com/rhcdemo/bdd-brms
Jenkins on OpenShift: http://bdd-rhcdemo.rhcloud.com/job/demo/
user//pass : admin//admin

ACCESSING THE PROJECT
1. Email sklenkar@redhat.com and bmeisele@redhat.com with your GitHub username stating that you want access to the BDD/BRMS demo and you will be made a collaborator shortly after.
2. Once you have been made a collaborator. clone the repo locally to your computer
1. Mac GUI: http://mac.github.com/, Guide: http://mac.github.com/help.html
2. Windows GUI: http://windows.github.com/, Guide: http://windows.github.com/help.html
3. Linux/ command line guide: https://help.github.com/articles/set-up-git
3. You now have access to all of the scenarios, rules and source java code necessary!
4. If you’d like to perform a sanity check, import it as an existing Maven Project in Eclipse and verify that there are no red Xs. Do a jUnit test of: /drools-reference-implementation/examples/insurance/src/test/java/com/rhc/insurance/RunCukesTest.java
You should see at least some tests pass. (There are some intentionally failing scenarios for demonstration).

VIEWING AND EDITING THE SCENARIOS
1. The scenarios are located at :
/drools-reference-implementation/examples/day-trader/src/test/resources/features
There is only one scenario file, titled dayTrader.feature, in the folder.
2. Open that file in your favorite text editor and add, remove or change scenarios as you please.
3. Commit your changes back to GitHub.
4. Go to the Jenkins project page linked above and verify that your commit is building by checking the Build History box on the left.
5. You may also view the file in your web browser at:
https://github.com/rhcdemo/bdd-brms/blob/master/drools-reference-implementation/examples/day-trader/src/test/resources/features/dayTrader.feature
For details on formatting your scenarios, see below.

VIEWING AND EDITING THE RULES/DECISION TABLES
1. The rules are located at:
/drools-reference-implementation/examples/day-trader/src/main/resources/rules
There is one rules file, titled dayTrader_rules.drl, in the folder.
2. Open that file in your favorite text editor and add, remove or change scenarios as you please.
3. Commit your changes back to GitHub
4. Go to the Jenkins project page linked above and verify that your commit is building by checking the Build History box on the left.
For details on formatting your rules, see below.

VIEWING CUCUMBER REPORTS
1. Navigate to the Jenkins project page linked above
2. If you wish to view reports for the latest build (i.e. most recent commit to GitHub), click Cucumber Reports
3. If you wish to view reports for an earlier build, select that build from the Build History box on the left and then click Cucumber Reports
4. Here, you will see two pie graphs showing the status of your tests
1. The one on the right shows what percentage of your scenarios passed and what percentage failed
2. The one on the left shows what percentage of your steps passed, what percentage failed and what percentage were skipped
5. Below that, you will see statistics for each of the .feature files that were ran. In this case, there is only the one.
6. Click the name of the Feature to see specifically which scenarios and steps in that feature passed, which failed and which were skipped. It also shows error reports for any failed steps.

FORMATTING SCENARIOS
All the scenarios in this project must follow this format:

@{ScenarioTag}
Scenario: {Scenario title as it will appear on Cucumber Report}

Given a current price of "{value}" for a stock "{name}"

And a day open of "{value}"

And a daily volatility of "{value}"

When determining an action for stock "{name}"

Then "{action}" stock "{name}" (for "{value}" higher/lower)*

{name} must be the ticker symbol for the stock you want to use
{value} must be a value for the corresponding field (assumes floating point notation)
{action} must be one of the following:
"ask to sell"
"bid to buy"
"do not ask to sell"
"do not bid to buy"

* The "for '{value}' higher/lower" should only be added when the "ask to sell" or "bid to buy" actions are used. The value should be the USD above or below the current price that the stock should be traded at.


FORMATTING RULES
-com.rhc.day-trader.rules is the package location of the rule in the directory structure
-Import com.rhc.stock.* tells the rule to import the class in that package (Stock, StockDay, StockQuote)
-Import com.rhc.trade.* tells the rule to import the class in that package (Trade, TradeRequest, TradeResponse)
