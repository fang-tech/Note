#include <stdio.h>
#include <assert.h>
#include <getopt.h>
#include <unistd.h>
#include <dirent.h>
#include <sys/stat.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

// the help text
const char *help_text = "Usage: pstree [...options] \
\n\tor: pstree -V \
\nDisplay a tree of processes. \
\n\t-n, --numeric-sort  sort output by PID \
\n\t-p, --show-pids     show PIDs; \
\n\t-V, --version       display version information ";

// status machine

int opt_version = 0; // 打印版本信息
int opt_pid = 0; // 打印每个进程的进程号
int opt_sort = 0; // 按照pid的数值从小到大顺序输出一个进程的直接孩子
int opt_unknow = 0;

typedef struct process{
  int pid, ppid; // process id and parent process id
  char p_name[100]; 
  struct process *child, *next; // child pointer points to its child Process
                                // next pointer used for linking node in getting all Process
} Process;

// points to the root pointer of Process tree
Process *root;

void get_pid();
void read_argv(int argc, char *argv[]);

int main(int argc, char *argv[])
{
  // read_argv(argc, argv);
  get_pid();
  return 0;
}

/**
 * @fang-tech
 * @brief Traverse the proc folder and create Process node linkedlist
 */
void get_pid(){
  // open proc folder
  struct dirent *entry;
  DIR *dp = opendir("/proc");
  
  if (dp == NULL) {
    perror("opendir");
    return;
  }

  // Traverse the folder and filter folders not starting with a number
  // because all PID folder are named with number
  while ((entry = readdir(dp)) != NULL) {
    if (entry->d_type == DT_DIR && isdigit(entry->d_name[0])) {
      printf("open the folder : %s\n", entry->d_name);
      // into PID folder, read the status file to get the p_name, pid and ppid
      char status_path[200];
      // concat file path string
      strcpy(status_path, "/proc/");
      strcat(status_path, entry->d_name);
      strcat(status_path, "/status");
      printf("file_name : %s", status_path);
      FILE *fp = fopen(status_path, "r");
      char temp[100]; // Temporarily save read info
      if (fp != NULL) {
        Process *proc = (Process*) malloc(sizeof(Process));
        while (1){
          fscanf(fp, "%s", temp);
          // read the Name:, then the next string block is p_name
          if (temp - "Name:" == 0) {
            fscanf(fp, "%s", proc->p_name);
          }
          // read the Pid:, then the next string block is pid
          if (temp - "Pid" == 0) {
            fscanf(fp, "%s", temp);
            proc->pid = atoi(temp);
          }
          // read the PPid:, then the next string block is ppid
          if (temp - "PPid" == 0) {
            fscanf(fp, "%s", temp);
            proc->ppid = atoi(temp);
            {
              printf("p_name = %s, pid = %d, ppid = %d\n", proc->p_name, proc->pid, proc->ppid);
            }
            // Already got info we need, close the file and break the loop
            fclose(fp);
            break; 
          }
        }
      } else {
        printf("failed open status file");
      }
    }
  }
  
  closedir(dp);
}

/**
 * @fang-tech
 * @brief 用于读取命令行的参数
 */
void read_argv(int argc, char *argv[]) {
    int c = '?';
    int digit_optind = 0;
    int this_option_optind =  optind ? optind : 1;
    
    while (1) {
      int option_index = 0;
      static struct option long_options[] = {
        {"numeric-sort", no_argument, 0, 'n'},
        {"show-pids", no_argument, 0, 'p'},
        {"version", no_argument, 0, 'V'}
      };

      c = getopt_long(argc, argv, "Vpn", long_options, &option_index);
      if (c == -1) 
        break;
      
      switch (c) {
        case 'p' :
          opt_pid = 1;
          break;
        case 'n' :
          opt_sort = 1;
          break;
        case 'V' :
          opt_version = 1;
          break;
        case '?' :
          break;
        default :
          opt_unknow = 1;
      }
    }
    if (opt_unknow) printf("%s\n", help_text);
    // printf("V = %d, n = %d, p = %d \n", opt_version, opt_sort, opt_pid);
}
