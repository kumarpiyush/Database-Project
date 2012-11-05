index=0;
for name in $(ls *.jpeg)
do
    mv "${name}" "${index}.jpeg"
    index=$((index+1))
done
