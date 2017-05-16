
line = input()
opgaver = []
for i in range(int(line)+1):
    line = input()
    if line:
        opgaver.append(line)
    else:
        break
opgave = opgaver
opgave = opgave[:-1]
''' Takes the first number in opgave and puts it in NMT'''
NMT = opgave.__getitem__(0)
L, M, T = [], [], []
L = opgave

for i in range(L.__len__()):
    if L[i].__getitem__(0) == "T":
        T.append(L[i][2:])
    if L[i].__getitem__(0) == "M":
        M.append(L[i][2:])
def SSplit(m):
    plus, minus, zero = list(m),list(m),list(m)
    for i in range(m.__len__()):
        if m[i] == '?':
            plus[i] = "+"
            minus[i] = "-"
            zero[i] = "0"
    Pats = [plus, minus, zero]
    print(SerializeSearch(T,Pats).__getitem__(-1))

def StringSearch(text, pattern):
    o, Tx, Px = 0, list(text), list(pattern)
    for x in range(Px.__len__()):
        if Px[x] == "?":
            SSplit(Px[x])
    Tx.reverse()
    Px.reverse()
    for i in range(Px.__len__()):
        if Px[i] == Tx[i]:
            o += 1
            if o == Px.__len__():
                return True
        else:
            return
def SerializeSearch(t,m):
    Matches = []
    for i in range(t.__len__()):
        for j in m:
            if StringSearch(t[i], j):
                Matches.append(i+1)
                m.pop()
    Matches.reverse()
    return Matches
liste = SerializeSearch(T,M)
s = ""
for i in range(liste.__len__()):
    s = s + "\n" + str(liste[i])
print(liste)
