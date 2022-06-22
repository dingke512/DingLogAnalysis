import os


def mkdir(path):
    folder = os.path.exists(path)
    if not folder:
        os.makedirs(path)
        print("---  new folder...  ---")
        print("---  OK  ---")
    else:
        print("---  There is this folder!  ---")


for i in range(1,32):
    date = str(i).zfill(2)
    file = "../lda/200608"+date
    mkdir(file)
