# Exercise: This code isn't great... what concrete problems does it have?
#           What makes it hard to read?

class planet:
  def __init__(s, x, d):
        s.planet_name = x
        s.distanceFromSun = d

def Findclosestplanet(ps, d):
   # Sorts the planets
   sorted(ps, key=lambda s: s.distanceFromSun)
   tot = 0
   old = ps[0]
   for p in ps:
     #print(p.distance_from_sun)
     if (p.distanceFromSun - old.distanceFromSun) * 0.5 + old.distanceFromSun > d:
       return old.planet_name
     # Redefines 'old'
     old = p
   return ps[-1]

xs = [
  planet("Mercury", 58), planet("Venus", 108),
  planet("Earth", 150),
  planet("Mars", 228),
  planet("Jupiter", 778),
  planet("Saturn", 1427),
  planet("Uranus", 2871),
  planet("Neptune", 4497),
  #planet("Pluto", 5913)
]
i = input("Distance in millions of km from the Sun? ")
print("Closest planet: " + Findclosestplanet(xs, int(i)))
