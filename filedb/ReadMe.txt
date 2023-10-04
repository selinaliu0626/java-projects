** Xiaonan Liu cs 622 HW2 **
-- project description
Merge given files into a single file, and search for the given keyword, print the contents related to these keywords

-- approach
merge files : checking the given target files, and scan the files we need to merge,then merge the contents according
to the headers, write in the target files
search: read the contents in the given file, if this line contains the keyword we need to search, add into the result list,
then print in given format
 call these two functions in main function in MergeAndSearch class

 --- unit test is provided to test the functions in Utils
