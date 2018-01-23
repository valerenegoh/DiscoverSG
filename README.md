# DiscoverSG
SUTD ISTD Term 3 First Android App Project

Despite being a small country, Singapore has a multitude of distinct attractions for tourists to
visit, from the magnificent Marina Bay Sands to the cultural streets of Katong. For the
free-and-easy tourist, it may be difficult to decide where, when and how to get around the
island. Our app is catered to meet these needs. 

For a broad overview, here is a youtube link demonstrating all functions and navigation in our app: https://youtu.be/0ArprZBJLEw

<img width="187" alt="capture" src="https://user-images.githubusercontent.com/23626462/35304854-076c1ac4-00d2-11e8-9742-b3f800b588f5.PNG">

## Contributors
UI design & SQLite Database: Valerene Goh
Algorithm: Tracy Yee & Cheryl Goh

## Report write-up

## Part 1: UI design & SQLite Database
(done by Valerene)

A bottom navigation bar is implemented to navigate between the following three fragments. In
addition, within “itinerary planning” optimisation function, there is a tab activity of the route.

### A. Browse Attractions
The first tab contains a wide selection of attractions listed into their respective categories.
Each attraction contains a short description of the place for the user to have a quick info
readup. If the user is interested in a particular attraction, he can add it to a new or existing
itinerary.

<img width="231" alt="attractions" src="https://user-images.githubusercontent.com/23626462/35304852-06cd0560-00d2-11e8-9314-2dc3fee79dca.PNG"><img width="230" alt="3" src="https://user-images.githubusercontent.com/23626462/35304794-dce9c364-00d1-11e8-87c2-f553cf6d5756.PNG"><img width="231" alt="newitinerary" src="https://user-images.githubusercontent.com/23626462/35304856-08a3cc2a-00d2-11e8-8f97-00f7f4575ca0.PNG"><img width="230" alt="4" src="https://user-images.githubusercontent.com/23626462/35304797-e0150f8a-00d1-11e8-84ee-8702b2f502df.PNG"><img width="229" alt="5" src="https://user-images.githubusercontent.com/23626462/35305649-17eae954-00d5-11e8-87f7-0a62b0146af0.PNG"><img width="230" alt="6" src="https://user-images.githubusercontent.com/23626462/35304800-e19f550e-00d1-11e8-9e9b-cd23030184bc.PNG"><img width="230" alt="7" src="https://user-images.githubusercontent.com/23626462/35304802-e2b03d00-00d1-11e8-9e36-caf6f1f820ac.PNG"><img width="231" alt="8" src="https://user-images.githubusercontent.com/23626462/35304803-e35819a8-00d1-11e8-8868-d0491a7aa9fe.PNG">

### B. Itinerary Planning
The second tab allows user to manage his itineraries: he can view its details, add a new
itinerary, delete all or individual itineraries and derive an optimal route based on all the
attractions listed in a particular itinerary. Upon entering a budget, an algorithm is run to derive
an optimal path for that itinerary that falls within the given budget, providing all the necessary
details on transport mode, time and expenditure. On the adjacent tab, the route is displayed
on a map fragment for an overarching view of the itinerary travel plan.

<img width="229" alt="13" src="https://user-images.githubusercontent.com/23626462/35305061-efd88932-00d2-11e8-811a-3a6be0eaa7c1.PNG"><img width="230" alt="14" src="https://user-images.githubusercontent.com/23626462/35305064-f0bbb84c-00d2-11e8-8e22-646bee3de49e.PNG"><img width="232" alt="15" src="https://user-images.githubusercontent.com/23626462/35305066-f164d788-00d2-11e8-8dd0-03e637eab030.PNG"><img width="230" alt="12" src="https://user-images.githubusercontent.com/23626462/35305058-ec6345f8-00d2-11e8-9cef-c8b88f6cb32c.PNG">
<img width="231" alt="9" src="https://user-images.githubusercontent.com/23626462/35304804-e3c7575a-00d1-11e8-860b-c5b12a689128.PNG"><img width="234" alt="10" src="https://user-images.githubusercontent.com/23626462/35304812-e4f986ca-00d1-11e8-9908-a50be4ccd51d.PNG"><img width="232" alt="11" src="https://user-images.githubusercontent.com/23626462/35304813-e5c17cd4-00d1-11e8-9f83-758d4a8f8c4d.PNG">

### C. Attractions Locator
The final tab of DiscoverSG allows tourists to search for any location they want that is within
Singapore and the view will be displayed on a map. The search view has a fuzzy spell
checker and autocomplete function of all the attractions in the database to produce quick
matches. If the location entered is not in Singapore, a toast will prompt the user to re-enter.

<img width="229" alt="spellchecker" src="https://user-images.githubusercontent.com/23626462/35304858-09595ff4-00d2-11e8-9b10-1f0c369223f1.PNG"><img width="229" alt="1" src="https://user-images.githubusercontent.com/23626462/35304788-d69a2aee-00d1-11e8-95ad-9b3682e0c90e.PNG"><img width="230" alt="2" src="https://user-images.githubusercontent.com/23626462/35304793-dbc75c8a-00d1-11e8-9599-a44630005f26.PNG"><img width="231" alt="malaysia" src="https://user-images.githubusercontent.com/23626462/35304855-0812c3ce-00d2-11e8-8287-c291c7a6a5dd.PNG">

## Android Features
● Recycler views are implemented in three parts of the app: each category page of “Browse
Attractions”, “Itinerary Planning” main page and the display route details of after
optimisation page.

● A single SQLite external database is implemented, which houses and chooses between
two internal database to run - one for attractions and one for itineraries. Each database
has its own custom set of methods to implement (DbManager). All attractions are loaded
from the json file “attractions.json” upon one-time creation of the table.
Note: A secondary data file “attractions2.json” containing 100 more attractions can be
uploaded by swapping the two file names and changing the attractions table version
under its DbHelper to x+1. However, these additional data do not contain to-from
time-cost details of various transport modes as they were not provided.

● As aforementioned, the raw folder contains internal JSON storage of the attractions. Each
attraction contains its name, category, details (which we generated separately using
Wikipedia API) and lat long (which is extraneous data).

● In Function 2, the fuzzy spellchecker computes the levenshtein distance between two
strings - the ones inputted by the user and the ones in our database. We set the similarity
factor to 0.7. This, compounded with the autocomplete function, allows more flexibility
than implementing a regex method, which requires the developer to update the regex
methods when new attractions are added to the attractions data.

● The categories page utilizes a custom gridview to house its images and texts. Upon
clicking on one of it, the intent passes an extra information - the name of the category -
which will call upon all the attractions in the database containing that category name.

# Part 2: Daily Itinerary Planning
(done by Tracy and Cheryl)

Data Collection: We collated the data provided at the end of the handout into corresponding
tables of costs and times needed to travel between locations and manually typed out into
three 3d arrays ( walkTrans , publicTrans and taxiTrans ) containing the cost-time information
from one destination to another for the corresponding transportation mode.

### A. Brute Force Solver
The brute force algorithm generates the optimal route for a given set of attractions (obtained
from user input) by systematically enumerating all possible candidates for the solution. Given
a list of n attractions, the total time complexity of the brute force algorithm is O(n*n!):
1. Add each attraction in a single permutation (total n attractions) to an ArrayList → O(n)
2. Add each permutation (n! possible permutations in total) to another ArrayList → O(n!)
3. Then, the algorithm computes the time-cost average of each path [O(n)] in each route
[O(n!)] by taking the average of the (time*cost) of the three transport modes (walk,
public transport, taxi), which is O(n*n!) total time complexity.
4. The algorithm finally compares the time-cost averages of all the possible
permutations to obtain the optimal travel route (route with the lowest time-cost average).

### B. Fast Solver
For our fast solver, we made use of the Nearest Neighbour (NN) algorithm as it is intuitive
and fast. To find the optimal route given n attractions, the NN algorithm starts with the initial
location (in this case, the Marina Bay Sands Hotel) and repeatedly visits the nearest unvisited
attraction until all the attractions have been visited. The algorithm then returns to the initial
location (in this case, the Marina Bay Sands Hotel). The time complexity of this algorithm is:
1. Find the nearest neighbour for each point → O(n)
2. Compute the time-cost average (mentioned above) from source to all points → O(n)
3. Repeat steps 1 and 2 n times for n attractions → O(n^2)
Hence, the total time complexity is O(n^2 + n^2) = O(n^2), which is polynomial (decently fast).

Since this algorithm is a greedy algorithm, it yields quick results but compromises on the
accuracy ( yields a path 25% longer than the shortest possible path on average). Hence, to
further optimise the “optimal” route generated by the NN algorithm, we used the 2-opt
technique (iteratively remove two edges and replace them with two different edges that
reconnect the fragments into a new and shorter route). Furthermore, starting with the NN
algorithm rather than using the 2-opt algorithm from scratch decreases the time complexity of
the 2-opt algorithm from O(n log(n)) to O(n). Hence, the total time complexity of the NN-2opt
algorithm is O(n^2 + n) = O(n^2) and the accuracy is further improved to give a reasonably
good solution that is close enough to the shortest possible path.

We then set the default transportation mode for each path in the optimal route to be by Taxi
as it is the fastest and hence most optimal. The total transport cost for the optimal route is
then calculated and the path with the shortest travel time is iteratively replaced with public
transport if the budget is exceeded (start replacing with walking if all paths are public
transport and budget is still exceeded). This is to ensure that the total transport time is
minimised while still staying within the constraint of the daily transport budget.

In conclusion, the fast solver, O(n^2), is faster than the brute force solver, O(n*n!), but it is
less accurate as it does not actually calculate out all the possible routes and their transport
times and costs (hence its speed). Hence, to ensure a balance of speed and accuracy, we
implement the brute force solver if the number of attractions entered is less than 10, and the
fast solver for 10 or more attractions. For testing, we ran the provided data on the mobile
phone with a budget of $20. The brute force solver took 2.6s while the fast solver took 2.4s,
which are close to the industry standard of <2s app loading time. Hence, we concluded that
for fewer attractions, the running time for both solvers are approximately the same, hence our
decision to use the brute force solver for fewer attractions as it is more accurate.
