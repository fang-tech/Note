all: sum

CFLAGS := -I$(TLIB_PATH)

sum: my_sum.c Makefile
	gcc -DNDEBUG $(CFLAGS) -o $@ $<

clean:
	rm -f my_sum
