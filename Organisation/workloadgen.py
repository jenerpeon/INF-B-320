import argparse
import random

# def generate_week(nworkdays, nhours):
# 	weekdays = []
#
# 	min_per_day = 3
# 	max_per_day = 5
#
# 	hours_left = nhours
#
# 	for i in len(range(nworkdays)):
# 		hours = random.randint(min_per_day,max_per_day)
# 		hours_left = hours_left - hours
# 		weekdays.append(hours)
#
#
# 	random.shuffle(weekdays)
#
# 	return weekdays


def generate_week(nworkdays, nhours):
	weekdays = []
	for i in range(nworkdays):
		weekdays.append(-1)
	for i in range(7-nworkdays):
		weekdays.append(0)

	random.shuffle(weekdays)

	min_per_day = 3
	max_per_day = 5

	hours_left = nhours

	for i in range(len(weekdays)):
		if hours_left <= 0:
			break

		""" here i did not work """
		if weekdays[i] == 0:
			continue

		""" here i did work	"""
		hours = random.randint(min_per_day,max_per_day)
		hours_left = hours_left - hours
		weekdays[i] = hours

	for i in range(len(weekdays)):
		if weekdays[i] == -1:
			weekdays[i] = 0

	return weekdays


if __name__ == "__main__":
	parser = argparse.ArgumentParser()
	parser.add_argument("--nweeks", default=1, type=int,
	                        help='number of weeks to be generated. default is 1')
	parser.add_argument("--kw", action='store_true',
	                        help='use and print kalenderwoche. first kw defaults to \'n\'')
	parser.add_argument("--first_kw", default=1,
	                        help='specify the first kw if used with option --kw. defaults is 1')

	args = parser.parse_args()

	for i in range(args.nweeks):
		print(generate_week(random.randint(4,6), 15))




