# Time to park some cars.

Your task is to add implementation to the ParkingManagerWithDifferentNeighboursByManufacturer class. More details about implementation details you can find there.

For better understanding we have 2 examples:

### Example 1
2-floors parking with 3 boxes on each floor and the next cars: __*AUDI, AUDI, VOLKSWAGEN, MAN, MAZDA, MAZDA*__.

So the next parking slots will be correct:
<br>1st floor: { [AUDI], [VOLKSWAGEN], [AUDI] }
<br>2nd floor: { [MAZDA], [MAN], [MAZDA] }

Or:
<br>1st floor: { [AUDI], [MAN], [MAZDA] }
<br>2nd floor: { [MAZDA], [VOLKSWAGEN], [AUDI] }

But next one will be wrong:
<br>1st floor: { [AUDI], [MAZDA], [MAZDA] }
<br>2nd floor: { [AUDI], [MAN], [VOLKSWAGEN] }

### Example 2
1-floor parking with 3 boxes on each floor and the next cars: __*AUDI, AUDI*__.

So the next parking slots will be correct:
<br>1st floor: { [AUDI], [ ], [AUDI] }

But next one will be wrong:
<br>1st floor: { [AUDI], [AUDI], [ ] }

We think, the code should be well tested to have stable code all the time, and find bugs before they go to the production. So, in addition to solve this small coding task, you also should provide your way of testing code.

Also, we are confident that we should not implement everything that we are asked blindly. If anything is unclear or confusing you can come and ask or clarify or even update some requirements or data structures if it would have more benefits. 
So, feel free to gather additional facts, or offer new ideas.

Once you finish working on this problem, please create patch of your changes and share it with us.

Good luck, have fun.